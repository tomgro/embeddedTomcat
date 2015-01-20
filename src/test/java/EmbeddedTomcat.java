

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.http.fileupload.FileUtils;

public class EmbeddedTomcat {
  private Tomcat tomcat = new Tomcat();

  public void start() {
    try {
      // If I don't want to copy files around then the base directory must be '.'
      String baseDir = ".";
      tomcat.setPort(8090);
      tomcat.setBaseDir(baseDir);
      tomcat.getHost().setAppBase(baseDir);
      tomcat.getHost().setDeployOnStartup(true);
      tomcat.getHost().setAutoDeploy(true);
      tomcat.start();
    } catch (LifecycleException e) {
      throw new RuntimeException(e);
    }
  }

  public void stop() {
    try {
      tomcat.stop();
      tomcat.destroy();
      // Tomcat creates a work folder where the temporary files are stored
      FileUtils.deleteDirectory(new File("work"));
    } catch (LifecycleException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void deploy() throws Exception {
	  System.out.println(new File("src/main/webapp").getAbsolutePath());
	  tomcat.addWebapp("/aa",new File("src/main/webapp").getAbsolutePath());
  }

  public String getApplicationUrl(String appName) {
    return String.format("http://%s:%d/%s", tomcat.getHost().getName(),
        tomcat.getConnector().getLocalPort(), appName);
  }
}
