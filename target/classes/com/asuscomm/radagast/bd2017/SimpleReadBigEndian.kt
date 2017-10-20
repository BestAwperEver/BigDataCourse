package com.asuscomm.radagast.bd2017
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.math.BigInteger;

fun main (args: Array<String>) {
	
	val inputFile: String = "D:/2GB.bin";
	val inputStream: InputStream = FileInputStream(inputFile);
	
	try {

		val N = 12;
		var i: Int;
		var j = 0;
		val bytes = ByteArray(1 shl N);
		var min_int: Int = Int.MAX_VALUE;
		var max_int: Int = Int.MIN_VALUE;
		var sum: BigInteger = BigInteger.valueOf(0);
		
		while (j < (1 shl (31 - N))) {
			val res = inputStream.read(bytes, 0, (1 shl N));
			if (res == -1) break;
			val bb: ByteBuffer = ByteBuffer.wrap(bytes);
			bb.order(ByteOrder.BIG_ENDIAN);
			i = 0;
			while (i < (1 shl N) / java.lang.Integer.BYTES) {
				val int: Int = bb.getInt();
				if (int < min_int) min_int = int;
				if (int > max_int) max_int = int;
				sum += BigInteger.valueOf(int.toLong());
				++i;
			}
			++j;
			if (j % 10000 == 0) {
				println(j);
			}
		}
		
		println("Max: ${max_int}");
		println("Min: ${min_int}");
		println("Sum: ${sum}");

	} catch (ex: IOException) {

		ex.printStackTrace();

	} finally {
		
		inputStream.close();
		
	}

}