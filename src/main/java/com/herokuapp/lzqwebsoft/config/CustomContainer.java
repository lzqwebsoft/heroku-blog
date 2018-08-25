package com.herokuapp.lzqwebsoft.config;

import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.servlet.api.DeploymentInfo;
import org.apache.jasper.deploy.JspPropertyGroup;
import org.apache.jasper.deploy.TagLibraryInfo;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.embedded.UndertowWebServerFactoryCustomizer;
import org.springframework.boot.web.embedded.undertow.ConfigurableUndertowWebServerFactory;
import org.springframework.boot.web.embedded.undertow.UndertowDeploymentInfoCustomizer;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import io.undertow.jsp.HackInstanceManager;
import io.undertow.jsp.JspServletBuilder;

import java.util.HashMap;

/**
 * 由于Undertow默认不支持JSP,故而需要手动的添加JSP的装载支持
 * @link https://github.com/monegask1969/jsp-demo-parent
 * @link http://hillert.blogspot.com/2016/04/spring-boot-with-jsps-using-undertow.html
 * @link https://www.baeldung.com/embeddedservletcontainercustomizer-configurableembeddedservletcontainer-spring-boot
 */
@Component
public class CustomContainer extends UndertowWebServerFactoryCustomizer {

    public CustomContainer(Environment environment, ServerProperties serverProperties) {
        super(environment, serverProperties);
    }

    @Override
    public void customize(ConfigurableUndertowWebServerFactory undertow) {

        final UndertowDeploymentInfoCustomizer customizer = new UndertowDeploymentInfoCustomizer() {
            @Override
            public void customize(DeploymentInfo deploymentInfo) {
                deploymentInfo.setClassLoader(CustomContainer.class.getClassLoader())
                        .setContextPath("/")
                        .setDeploymentName("heroku-blog.war")
                        .setResourceManager(new DefaultResourceLoader(CustomContainer.class))
                        .addServlet(JspServletBuilder.createServlet("Default Jsp Servlet", "*.jsp"));

                final HashMap<String, TagLibraryInfo> tagLibraryInfo = TldLocator.createTldInfos();
                JspServletBuilder.setupDeployment(deploymentInfo, new HashMap<String, JspPropertyGroup>(), tagLibraryInfo, new HackInstanceManager());
            }
        };

        undertow.addDeploymentInfoCustomizers(customizer);
    }

    public static class DefaultResourceLoader extends ClassPathResourceManager {
        public DefaultResourceLoader(final Class<?> clazz) {
            super(clazz.getClassLoader(), "");
        }
    }
}
