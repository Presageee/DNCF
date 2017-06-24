package org.dncf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DNCClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(DNCClientApplication.class, args);

//		int size = Runtime.getRuntime().availableProcessors() + 1;
//		System.out.println("cpu available:" + size);
//		System.out.println("start load data");
//		long ms = System.currentTimeMillis();
//		RandomMemoryStorage randomMemoryStorage = new RandomMemoryStorage();
//		randomMemoryStorage.load();
//		System.out.println("load completed:" + (System.currentTimeMillis() - ms) + "ms");
//		//int size = Integer.parseInt(args[0])
//		int block = RandomMemoryStorage.featureVectors.size() / size;
//
//		Random random = new Random();
//		for (int j = 0; j < 10; j++) {
//			float data[] = new float[32];
//			for (int i = 0; i < 32; i++) {
//				data[i] = random.nextFloat();
//			}
//			System.out.println("start search task");
//			long ms2 = System.currentTimeMillis();
//			ExecutorService executorService = Executors.newCachedThreadPool();
//			Queue<Future<List<Float>>> queue = new LinkedList<>();
//			for (int i = 0; i < size; i++) {
//				int begin = i * block;
//				int end = begin + block;
//				if (end > RandomMemoryStorage.featureVectors.size()) {
//					end	= RandomMemoryStorage.featureVectors.size();
//				}
//				SearchTask task = new SearchTask(data, begin, end, "task-" + i);
//				queue.add(executorService.submit(task));
//			}
//
//			HeapMin min = new HeapMin(30);
//			while (!queue.isEmpty()) {
//				try {
//					List<Float> datas = queue.remove().get();
//					int len = datas.size();
//					for (int i = 0; i < len; i++) {
//						min.add(datas.get(i));
//					}
//				} catch (InterruptedException | ExecutionException e) {
//					e.printStackTrace();
//				}
//
//			}
//			System.out.println(min.getHeap());
//			System.out.println("search completed:" + (System.currentTimeMillis() - ms2) + "ms");
//			System.out.println();
//			System.out.println();
//			System.out.println();
//			System.out.println();
//		}

	}
}
