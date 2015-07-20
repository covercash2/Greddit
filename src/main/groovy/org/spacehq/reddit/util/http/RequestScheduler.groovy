package org.spacehq.reddit.util.http

/**
 * A scheduler for HTTP requests.
 */
class RequestScheduler extends Thread {

    /**
     * The HTTP request queue.
     */
    final Queue queue = [] as Queue
    /**
     * The minimum amount of time between calls.
     */
    long millisBetweenCalls = 0
    /**
     * Whether the scheduler is running.
     */
    boolean running = true

    /**
     * Creates and starts a new RequestScheduler instance.
     */
    RequestScheduler() {
        super("RequestScheduler-" + new Random().nextInt())
        this.start()
    }

    @Override
    void run() {
        long last = 0
        while(this.running) {
            if(System.currentTimeMillis() - last > this.millisBetweenCalls) {
                if(this.queue.size() > 0) {
                    last = System.currentTimeMillis()
                    this.queue.poll()()
                }
            }
        }
    }
}
