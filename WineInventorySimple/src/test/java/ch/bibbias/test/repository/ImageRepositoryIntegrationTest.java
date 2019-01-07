package ch.bibbias.test.repository;

import static org.junit.Assert.assertEquals;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ch.bibbias.bean.Image;
import ch.bibbias.repository.ImageRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ImageRepositoryIntegrationTest {

	@Autowired
	ImageRepository imageRepository;

	@Test
	public void whenSaveImage_thenFindSameImage() {

		// given
		Image image = new Image();
		// InputStream is = getClass().getResourceAsStream("/img/Wine2.jpeg");

		BufferedImage bImage;
		try {
			bImage = ImageIO.read(new File("/img/Wine2.jpeg"));
			ByteArrayOutputStream bos = new ByteArrayOutputStream();

			ImageIO.write(bImage, "jpg", bos);
			byte[] data = bos.toByteArray();
			image.setName("Testbild");
			image.setType("JPG");
			image.setData(data);
			imageRepository.save(image);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// when
		Image found = imageRepository.findByName("Testbild");

		// then
		assertEquals(found.getName(), image.getName());


	}

	public static byte[] readBytes(InputStream stream) throws IOException {
		if (stream == null)
			return new byte[] {};
		byte[] buffer = new byte[1024];
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		boolean error = false;
		try {
			int numRead = 0;
			while ((numRead = stream.read(buffer)) > -1) {
				output.write(buffer, 0, numRead);
			}
		} catch (IOException e) {
			error = true; // this error should be thrown, even if there is an error closing stream
			throw e;
		} catch (RuntimeException e) {
			error = true; // this error should be thrown, even if there is an error closing stream
			throw e;
		} finally {
			try {
				stream.close();
			} catch (IOException e) {
				if (!error)
					throw e;
			}
		}
		output.flush();
		return output.toByteArray();
	}

}
