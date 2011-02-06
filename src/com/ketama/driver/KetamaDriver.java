package com.ketama.driver;

import com.ketama.main.*;

public class KetamaDriver {

	static {
		String[] serverlist = { "localhost:2001" };
		Integer[] weights = { new Integer(5), new Integer(2) };
		int initialConnections = 5;
		int minSpareConnections = 5;
		int maxSpareConnections = 50;
		long maxIdleTime = 1000 * 60 * 30; // 30 minutes
		long maxBusyTime = 1000 * 60 * 5; // 5 minutes
		long maintThreadSleep = 1000 * 5; // 5 seconds
		int socketTimeOut = 1000 * 3; // 3 seconds to block on reads
		int socketConnectTO = 1000 * 3; // 3 seconds to block on initial
										// connections. If 0, then will use
										// blocking connect (default)
		boolean failover = false; // turn off auto-failover in event of server
									// down
		boolean nagleAlg = false; // turn off Nagle's algorithm on all sockets
									// in pool

		SockIOPool pool = SockIOPool.getInstance();
		pool.setServers(serverlist);
		pool.setWeights(weights);
		pool.setInitConn(initialConnections);
		pool.setMinConn(minSpareConnections);
		pool.setMaxConn(maxSpareConnections);
		pool.setMaxIdle(maxIdleTime);
		pool.setMaxBusyTime(maxBusyTime);
		pool.setMaintSleep(maintThreadSleep);
		pool.setSocketTO(socketTimeOut);
		pool.setSocketConnectTO(socketConnectTO);
		pool.setNagle(nagleAlg);
		pool.setHashingAlg(SockIOPool.NEW_COMPAT_HASH);
		pool.initialize();
	}

	public static void main(String[] args) {
		SockIOPool.SockIO sock = SockIOPool.getInstance().getSock("100");

		try {
			sock.write("1".getBytes());
			sock.flush();
			System.out.println("Version: " + sock.readLine());
		}

		catch (Exception ioe) {
			System.out.println("io exception thrown");
		}

		sock.close();
	}
}
