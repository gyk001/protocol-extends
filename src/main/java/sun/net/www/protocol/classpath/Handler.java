package sun.net.www.protocol.classpath;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

/**
 * <code>classpath:</code>协议处理器，扩展URL。
 * 
 * @author <a href="mailto:gyk001@gmail.com">Guo Yukun(gyk001@gmail.com)</a>
 * @version 2014年7月30日
 * 
 */
public class Handler extends URLStreamHandler {

	@Override
	protected URLConnection openConnection(URL u) throws IOException {
		return new ClassPathConnection(u);
	}

	static final class ClassPathConnection extends URLConnection {
		private String resource;

		protected ClassPathConnection(URL url) {
			super(url);
			this.doInput = false;
			this.doOutput = true;
			this.connected = false;
			this.resource = url.getPath();
		}

		@Override
		public void connect() throws IOException {
			InputStream is = null;
			try {
				is = Handler.class.getResourceAsStream(resource);
				if(is==null){
					throw new FileNotFoundException("资源未找到:"+resource);
				}
			} finally {
				if (is != null) {
					is.close();
				}
			}
		}

		@Override
		public InputStream getInputStream() throws IOException {
			return Handler.class.getResourceAsStream(resource);
		}

		@Override
		public String getContentType() {
			return "application/octet-stream";
		}

		@Override
		public String toString() {
			return "ClassPathConnection [resource=" + resource + "]";
		}
	}
}
