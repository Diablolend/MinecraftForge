package cpw.mods.fml.client;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.logging.log4j.Level;

import javax.imageio.ImageIO;

import net.minecraft.client.resources.FolderResourcePack;

import com.google.common.base.Charsets;

import cpw.mods.fml.common.FMLContainerHolder;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.FMLModContainer;
import cpw.mods.fml.common.ModContainer;

public class FMLFolderResourcePack extends FolderResourcePack implements FMLContainerHolder {

    private ModContainer container;
    private static final boolean DEBUG_FFRP = Boolean.parseBoolean(System.getProperty("fml.debugFolderResourcePack", "false"));

    public FMLFolderResourcePack(ModContainer container)
    {
        super(container.getSource());
        this.container = container;
    }

    @Override
    protected boolean hasResourceName(String p_110593_1_)
    {
        return super.hasResourceName(p_110593_1_);
    }
    @Override
    public String getPackName()
    {
        return "FMLFileResourcePack:"+container.getName();
    }
    @Override
    protected InputStream getInputStreamByName(String resourceName) throws IOException
    {
        try
        {
            return super.getInputStreamByName(resourceName);
        }
        catch (IOException ioe)
        {
            if ("pack.mcmeta".equals(resourceName))
            {
                if (DEBUG_FFRP) FMLLog.log(container.getName(), Level.DEBUG, "Mod %s is missing a pack.mcmeta file, substituting a dummy one", container.getName());
                return new ByteArrayInputStream(("{\n" +
                        " \"pack\": {\n"+
                        "   \"description\": \"dummy FML pack for "+container.getName()+"\",\n"+
                        "   \"pack_format\": 1\n"+
                        "}\n" +
                        "}").getBytes(Charsets.UTF_8));
            }
            else throw ioe;
        }
        //return super.getInputStreamByName(resourceName);
    }

    @Override
    public BufferedImage getPackImage() throws IOException
    {
        return ImageIO.read(getInputStreamByName(container.getMetadata().logoFile));
    }

    @Override
    public ModContainer getFMLContainer()
    {
        return container;
    }

}
