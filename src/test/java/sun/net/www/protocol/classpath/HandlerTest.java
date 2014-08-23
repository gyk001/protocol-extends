package sun.net.www.protocol.classpath;

import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class HandlerTest {

	@DataProvider
	public Object[][] dpClasspathFile() {
		return new Object[][] {
				new Object[] { "/folder/classpath-main-test.txt" ,"main目录下资源"},
				new Object[] { "/folder/classpath-test.txt" , "test目录下资源"},
				new Object[] { "/folder/classpath test.txt","文件名包含空格资源" },
				new Object[] { "/folder/classpath-测试.txt","中文目录资源" },
				new Object[] { "/folder/classpath 测试.txt","中文件名包含空格资源" },
				new Object[] { "/anthor-folder/../folder/classpath 测试.txt","上级目录(../)资源" },
				new Object[] { "/anthor-folder/../folder/../anthor-folder/abc.txt","多上级目录(../)资源" }
				
		};
	}

	@BeforeClass
	private void beforClass(){
		//Handler.init();
	}
	
	@Test(dataProvider = "dpClasspathFile",description="测试classpath的URL协议处理器")
	public void openConnection(String res, String resDesc) throws IOException {
		//目标文件内容
		String text = IOUtils.toString(HandlerTest.class
				.getResourceAsStream(res));
		Assert.assertNotNull(text,"保证测试文件内容不为空");
		Assert.assertNotEquals(text, "","保证测试文件内容不为空");
		URL url = new URL("classpath:" + res);
		String x = IOUtils.toString(url);
		System.out.println(text);
		Assert.assertEquals(x, text, "场景："+resDesc);
	}

}
