package io.github.lxxbai.jvs.component;

import com.jfoenix.svg.SVGGlyph;
import io.github.lxxbai.jvs.common.util.SVGGlyphUtil;
import javafx.beans.NamedArg;

/**
 * @author lxxbai
 */
public class XxbSvg extends SVGGlyph {

    public XxbSvg(@NamedArg("path") String path) {
        super(SVGGlyphUtil.getSvgContent(path));
    }
}
