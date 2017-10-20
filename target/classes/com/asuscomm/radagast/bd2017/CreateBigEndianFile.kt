package com.asuscomm.radagast.bd2017
import java.io.*;
import java.util.Random;

fun Int.bigEndianBytes() = 
    java.nio.ByteBuffer.allocate(java.lang.Integer.BYTES)
             .putInt(java.lang.Integer.reverseBytes(this))
             .array()

fun main (args: Array<String>) {
	
	val outputFile: String = "D:/2GB.bin";
	val outputStream: OutputStream = FileOutputStream(outputFile);
	
	try {

		val r = Random(42);
		val N = 12;
		var i = 0;
		var j = 0;
		val bytes_4k = ByteArray(1 shl N);
		
		while (j < (1 shl (31 - N))) {
			while (i < (1 shl N)) {
				val bytes = r.nextInt().bigEndianBytes();
				for (byte in bytes) {
					bytes_4k[i] = byte;
					++i;
				}
			}
			outputStream.write(bytes_4k);
			++j;
			if (j % 10000 == 0) {
				println(j);
			}
		}	

	} catch (ex: IOException) {

		ex.printStackTrace();

	} finally {
		
		outputStream.close();
		
	}

}