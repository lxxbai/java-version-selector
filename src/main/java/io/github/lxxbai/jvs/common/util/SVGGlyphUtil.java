package io.github.lxxbai.jvs.common.util;

import com.jfoenix.svg.SVGGlyph;
import javafx.scene.paint.Color;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * @author lxxbai
 */
@Slf4j
public class SVGGlyphUtil {

    /**
     * 加载SVG图标
     *
     * @param path path
     * @return SVGGlyph
     */
    public static SVGGlyph loadGlyph(String path) {
        URL url = ResourceUtil.getUrl(path);
        return loadGlyph(url);
    }

    /**
     * 加载SVG图标
     *
     * @param path path
     * @return SVGGlyph
     */
    public static String getSvgContent(String path) {
        try {
            URL url = ResourceUtil.getUrl(path);
            return  extractSvgPath(getStringFromInputStream(url.openStream()));
        } catch (IOException e) {
            log.error("loadGlyph error", e);
            return "";
        }
    }


    /**
     * 加载SVG图标
     *
     * @param url url
     * @return SVGGlyph
     */
    public static SVGGlyph loadGlyph(URL url) {
        String urlString = url.toString();
        String filename = urlString.substring(urlString.lastIndexOf('/') + 1);
        try {
            return new SVGGlyph(-1, filename, extractSvgPath(getStringFromInputStream(url.openStream())),
                    Color.BLACK);
        } catch (Exception e) {
            return new SVGGlyph(-1, filename, "",
                    Color.BLACK);
        }
    }

    private static String extractSvgPath(String svgString) {
        return svgString.replaceFirst(".*d=\"", "").replaceFirst("\".*", "");
    }

    private static String getStringFromInputStream(InputStream is) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception var13) {
            log.error("getStringFromInputStream error", var13);
        }
        return sb.toString();
    }
}
