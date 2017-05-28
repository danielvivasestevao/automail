class Sleeper {

    /**
     * Makes the thread sleep for a given amount of seconds and updates
     * System.out every second with the remaining amount of sleep time.
     * @param secondsToSleep
     */
    static void sleep(final int secondsToSleep) throws InterruptedException {
        int countDown = secondsToSleep > 0 ? secondsToSleep : 0;
        System.out.print(String.format("Sleeping for %d seconds...", countDown));
        while (countDown > 0) {
            Thread.sleep(1000);
            System.out.print(String.format("\rSleeping for %d seconds...", countDown));
            countDown--;
        }
    }

}
