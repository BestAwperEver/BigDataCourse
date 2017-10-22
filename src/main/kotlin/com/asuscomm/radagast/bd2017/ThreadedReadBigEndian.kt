package com.asuscomm.radagast.bd2017

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.ByteOrder;
import java.math.BigInteger;

val threadNum = 4;
var min_ints = IntArray(threadNum);
var max_ints = IntArray(threadNum);
var sums = Array<BigInteger>(threadNum, {BigInteger.valueOf(0)});
val threads = Array<Thread>(threadNum, {i->Thread(ReadThread(i))});

class ReadThread(val threadIndex: Int) : Runnable {
    override fun run() {
    	val inputFile: String = "D:/2GB.bin";
		val fileChannel: FileChannel = RandomAccessFile(File(inputFile), "r").getChannel();
		
		fileChannel.use {
			val buffer: MappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY,
				threadIndex * fileChannel.size() / threadNum,
				fileChannel.size() / threadNum);
			buffer.order(ByteOrder.BIG_ENDIAN);
			while (buffer.hasRemaining()) {
				val int: Int = buffer.getInt();
				if (int < min_ints[threadIndex]) min_ints[threadIndex] = int;
				if (int > max_ints[threadIndex]) max_ints[threadIndex] = int;
				sums[threadIndex] += BigInteger.valueOf(int.toLong());
			}
		}
		
		println("Min (${threadIndex}): ${min_ints[threadIndex]}");
		println("Max (${threadIndex}): ${max_ints[threadIndex]}");
		println("Sum (${threadIndex}): ${sums[threadIndex]}");
		
    }
}

fun main (args: Array<String>) {
	val startTime = System.nanoTime();
	for (i in 0..threadNum-1) {
		threads[i].start();
	}
	for (i in 0..threadNum-1) {
		threads[i].join();
	}
	println("Min: ${min_ints.min()}");
	println("Max: ${max_ints.max()}");
	var sum: BigInteger = BigInteger.valueOf(0);
	for (i in 0..threadNum-1) {
		sum += sums[i];
	}
	println("Sum: ${sum}");
	val endTime = System.nanoTime();
	println("Took " + (endTime - startTime)/1000000 + " ms"); 
}