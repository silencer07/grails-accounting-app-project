import accountinggroup.web.SyncJob

class BootStrap {

    def importerService

    def init = { servletContext ->
        importerService.startImport()

        SyncJob.schedule(SyncJob.MIDNIGHT_CRON_EXPRESSION, [:])
    }
    def destroy = {
    }
}
