class BootStrap {

    def importerService

    def init = { servletContext ->
        importerService.startImport()
    }
    def destroy = {
    }
}
