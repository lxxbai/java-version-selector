package io.github.lxxbai.javaversionselector.common.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.XmlUtil;
import com.jfoenix.svg.SVGGlyph;
import javafx.scene.paint.Color;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * @author lxxbai
 */
public class SVGGlyphUtil {


    /**
     * 加载SVG图标
     *
     * @param url url
     * @return SVGGlyph
     */
    @SneakyThrows
    public static SVGGlyph loadGlyph(URL url) {
        String urlString = url.toString();
        String filename = urlString.substring(urlString.lastIndexOf('/') + 1);
        return new SVGGlyph(-1, filename, extractSvgPath(getStringFromInputStream(url.openStream())),
                Color.BLACK);
    }

    private static String extractSvgPath(String svgString) {
        return svgString.replaceFirst(".*d=\"", "").replaceFirst("\".*", "");
    }

    private static String getStringFromInputStream(InputStream is) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        try {
            br = new BufferedReader(new InputStreamReader(is));

            String line;
            while((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException var13) {
            IOException e = var13;
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException var12) {
                    IOException e = var12;
                    e.printStackTrace();
                }
            }

        }

        return sb.toString();
    }
}
