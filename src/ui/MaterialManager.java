package ui;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;

import java.util.HashMap;
import java.util.Map;

/**
 * @author      Danil Platonov <slemonide@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * Generates and stores materials
 */
public class MaterialManager {
    private static MaterialManager instance;
    private Material defaultMaterial;
    private Map<ColorRGBA, Material> coloredMaterials;

    private MaterialManager() {
        coloredMaterials = new HashMap<>();
    }

    /**
     * Singleton pattern
     * @return the current active instance of the world
     */
    public static MaterialManager getInstance() {
        if (instance == null){
            instance = new MaterialManager();
        }
        return instance;
    }

    /**
     * @return material made of "Common/MatDefs/Misc/ShowNormals.j3md"
     */
    private Material getDefaultMaterial(AssetManager assetManager) {
        if (defaultMaterial == null) {
            defaultMaterial = new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
        }
        return defaultMaterial;
    }

    /**
     * @param color fill color for the whole material
     * @return colored material made of "Common/MatDefs/Misc/Unshaded.j3md"
     */
    Material getColoredMaterial(AssetManager assetManager, ColorRGBA color) {
        if (color == null) {
            return getDefaultMaterial(assetManager);
        }

        if (!coloredMaterials.containsKey(color)) {
            Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
            mat.setColor("Color", color);

            coloredMaterials.put(color, mat);
        }

        return coloredMaterials.get(color);
    }
}
