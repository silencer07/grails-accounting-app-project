<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <title>Create T-Account Entry</title>
    </head>
    <body>
        <a href="#list-transactionTemp" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div id="list-transactionTemp" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            Description : <g:textField name="description" value="" /> Document Date : <g:datePicker name="documentDate" value="" default="${new Date()}"/>
            <table>
            </table>
        </div>
    </body>
</html>