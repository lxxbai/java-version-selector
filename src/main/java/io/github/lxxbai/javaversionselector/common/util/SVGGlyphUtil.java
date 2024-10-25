package io.github.lxxbai.javaversionselector.common.util;

import cn.hutool.core.io.IoUtil;
import com.jfoenix.svg.SVGGlyph;
import javafx.scene.paint.Color;
import lombok.SneakyThrows;

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
        return new SVGGlyph(-1, filename, extractSvgPath(IoUtil.read(url.openStream(), Charset.defaultCharset())), Color.BLACK);
    }

    private static String extractSvgPath(String svgString) {
        return svgString.replaceFirst(".*d=\"", "").replaceFirst("\".*", "");
    }
}
