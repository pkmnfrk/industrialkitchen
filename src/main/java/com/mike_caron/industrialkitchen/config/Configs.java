package com.mike_caron.industrialkitchen.config;

import com.mike_caron.industrialkitchen.IndustrialKitchen;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fml.common.ModContainer;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Configs
{
    public static void load(ModContainer mod, File userDirectory)
    {
        CraftingHelper.findFiles(mod, "assets/" + IndustrialKitchen.modId + "/config", null, (root, url) -> loadConfig(url), false, true);

        if(userDirectory != null)
        {
            File[] files = userDirectory.listFiles();
            if(files != null)
            {
                for (File file : files)
                {
                    loadConfig(Paths.get(file.toURI()));
                }
            }
        }

    }

    private static boolean loadConfig(Path url)
    {
        File file = new File(url.toUri());

        if (file.isFile())
        {

            try
            {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = factory.newDocumentBuilder();
                try (InputStream stream = Files.newInputStream(url, StandardOpenOption.READ))
                {
                    Document document = documentBuilder.parse(stream);

                    handleDocument(document);

                    IndustrialKitchen.logger.info("Loaded config: {}", url);
                }

            }
            catch (Exception ex)
            {
                IndustrialKitchen.logger.error("Unable to load config " + url, ex);
            }
        }

        return false;
    }

    private static void handleDocument(Document document) throws Exception
    {
        switch(document.getDocumentElement().getTagName())
        {
            case "fluids":
                FluidConfig.loadConfig(document);
                break;
            default:
                throw new Exception("Unknown config type: " + document.getDocumentElement().getTagName());
        }
    }
}
