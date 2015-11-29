package accountinggroup.web

class SyncJob {

    public static final MIDNIGHT_CRON_EXPRESSION = "0 0 0 * * ?"

    def mongoDBService

    def execute() {
        log.info("Executing Sync Job......")
        mongoDBService.syncToDatabase()
        log.info("Executing Finished......")
    }
}
