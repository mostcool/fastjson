package com.alibaba.json.bvt.support.jaxrs.mock;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;

import javax.ws.rs.core.MediaType;

import junit.framework.TestCase;

import org.junit.Assert;

import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.jaxrs.FastJsonProvider;

public class FastJsonProviderTest extends TestCase {

	@SuppressWarnings("deprecation")
	public void test_1() throws Exception { 
		
		FastJsonProvider provider1 = new FastJsonProvider("UTF-8");
		Assert.assertEquals("UTF-8", provider1.getCharset().name());
		
		FastJsonProvider provider = new FastJsonProvider(new Class[0]);

		Assert.assertNotNull(provider.getFastJsonConfig());
		provider.setFastJsonConfig(new FastJsonConfig());
		
		provider.isReadable(VO.class, VO.class, null, MediaType.APPLICATION_JSON_TYPE);
		provider.isWriteable(VO.class, VO.class, null, MediaType.APPLICATION_JSON_TYPE);
		
		VO vo = (VO) provider.readFrom(null, VO.class, null, MediaType.APPLICATION_JSON_TYPE, null, new ByteArrayInputStream("{\"id\":123}".getBytes(Charset
				.forName("UTF-8"))));
		Assert.assertEquals(123, vo.getId());

		final ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		provider.writeTo(vo, VO.class, VO.class, null, MediaType.APPLICATION_JSON_TYPE, null, byteOut);
		
		byte[] bytes = byteOut.toByteArray();
		Assert.assertEquals("{\"id\":123}", new String(bytes, "UTF-8"));
		
		provider.getSize(vo, VO.class, VO.class, null, MediaType.APPLICATION_JSON_TYPE);
		
	}
	
	public static class VO {

		private int id;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

	}
}