<%@page import="com.admu.accountinggroup.domain.*" %>
<%@page import="com.admu.accountinggroup.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <title>${ update ? 'Update' : 'Create'} T-Account Entry</title>
    </head>
    <body>
            <div class="content scaffold-list" role="main">
                <h1>${ update ? 'Update' : 'Create'} T-Account Entry</h1>
                <div class="nav" role="navigation">
                    <ul>
                        <li><g:link class="home" action="index">Back</g:link></li>
                        <li><g:link class="create" action="sync">Sync Cache to DB</g:link></li>
                        <li><g:link class="home" controller="transactionAccountEntry" action="approvedEntries" target="_blank">Synced Data</g:link></li>
                        <li><g:link class="home" controller="quartz" action="list" target="_blank">Sync Sched. Details</g:link></li>
                        <li><g:link class="create" action="scheduleThreeMinutesFromNow">Sched. Sync in 3 mins.</g:link></li>
                    </ul>
                </div>
                <g:hasErrors bean="${cmd}">
                    <ul class="errors" role="alert">
                        <g:eachError bean="${cmd}" var="error">
                            <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                        </g:eachError>
                    </ul>
                </g:hasErrors>
                <g:if test="${flash.message}">
                    <div class="message" role="status">${flash.message}</div>
                </g:if>

                <g:form controller="deliverableThree" action="performReverseEntry" >
                    UUID : <g:textField name="uuid" value="${cmd?.uuid}"/>
                    <fieldset class="buttons">
                        <g:submitButton name="Reverse Entry" class="save"/>
                    </fieldset>
                </g:form>
            </div>
    </body>
</html>