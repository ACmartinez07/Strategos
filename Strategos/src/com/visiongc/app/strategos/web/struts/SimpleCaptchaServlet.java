/**
 * 
 */
package com.visiongc.app.strategos.web.struts;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nl.captcha.Captcha;
import nl.captcha.backgrounds.GradiatedBackgroundProducer;
import nl.captcha.servlet.CaptchaServletUtil;
import nl.captcha.text.renderer.ColoredEdgesWordRenderer;

/**
 * @author Kerwin
 *
 */
public class SimpleCaptchaServlet extends HttpServlet
{

    public SimpleCaptchaServlet()
    {
    }

    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        if(getInitParameter("captcha-height") != null)
            _height = Integer.valueOf(getInitParameter("captcha-height")).intValue();
        if(getInitParameter("captcha-width") != null)
            _width = Integer.valueOf(getInitParameter("captcha-width")).intValue();
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        ColoredEdgesWordRenderer wordRenderer = new ColoredEdgesWordRenderer(COLORS, FONTS);
        Captcha captcha = (new nl.captcha.Captcha.Builder(_width, _height)).addText(wordRenderer).gimp().addNoise().addBackground(new GradiatedBackgroundProducer()).build();
        CaptchaServletUtil.writeImage(resp, captcha.getImage());
        req.getSession().setAttribute("simpleCaptcha", captcha);
        req.getSession().setAttribute("simpleCaptchaKey", captcha.getAnswer());
    }

    private static final long serialVersionUID = 1L;
    private static int _width = 200;
    private static int _height = 50;
    private static final List<Color> COLORS;
    private static final List<Font> FONTS;

    static 
    {
        COLORS = new ArrayList<Color>(2);
        FONTS = new ArrayList<Font>(3);
        COLORS.add(Color.BLACK);
        COLORS.add(Color.BLUE);
        FONTS.add(new Font("Geneva", 2, 48));
        FONTS.add(new Font("Courier", 1, 48));
        FONTS.add(new Font("Arial", 1, 48));
    }
}
