<%@page import="com.admu.accountinggroup.domain.*" %>
<%@page import="com.admu.accountinggroup.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <title>${ update ? 'Update' : 'Create'} T-Account Entry</title>
    <script>
        function addRow() {
            var $tableBody = $("#entryDataBody");
            var rowCount = $tableBody.children().length;
            var $lastRow = $tableBody.children("tr:last");

            var toAppend = $lastRow.clone().find("input[type!='button']").val("").end().html();
            toAppend = toAppend.split("[" + (rowCount - 1) + "]").join("[" + rowCount +"]");

            $lastRow.after("<tr>" + toAppend + "</tr>");
            $("#add-row").prop('enabled',  true);
        }

        function removeRow(removeButton){
            $(removeButton).parent().parent().remove();
        }
    </script>
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

                <g:form controller="deliverableThree" action="${update ? 'updateTransactionEntry' : 'saveTransactionEntry'}" >
                    <div class="fieldcontain">
                        <label for="reference">Ref. Description</label>
                        <g:hiddenField name="uuid" value="${cmd?.uuid}"/>
                        <g:textField name="reference" value="${cmd?.reference}"/>
                    </div>
                    <div class="fieldcontain required">
                        <label for="documentDate">Date
                            <span class="required-indicator">*</span>
                        </label> <g:datePicker name="documentDate" precision="day" value="${cmd?.documentDate}" default="${new Date()}"/>
                    </div>
                    <br/>
                    <table>
                        <tr>
                            <th>Particular</th>
                            <th>Posting Key</th>
                            <th>Amount</th>
                            <th>Description</th>
                            <th>Comments</th>
                            <th></th>
                        </tr>
                        <tbody id="entryDataBody">
                            <g:if test="${!cmd?.entries}">
                                <tr>
                                    <td><g:select name="entries[0].accountId" from="${Account.list()}" optionKey="id"
                                        optionValue="nameAndSide"/></td>
                                    <td><g:select name="entries[0].postingKey" from="${Side.values()}"/></td>
                                    <td><g:textField name="entries[0].amount" size="8"/></td>
                                    <td><g:textField name="entries[0].description"/></td>
                                    <td><g:textField name="entries[0].comments"/></td>
                                    <td><input type="button" value="X" onclick="removeRow(this)" style="color: red; font-weight: 700;"/></td>
                                </tr>
                                <tr>
                                    <td><g:select name="entries[1].accountId" from="${Account.list()}" optionKey="id"
                                        optionValue="nameAndSide"/></td>
                                    <td><g:select name="entries[1].postingKey" from="${Side.values()}" value="${Side.CR}"/></td>
                                    <td><g:textField name="entries[1].amount" size="8"/></td>
                                    <td><g:textField name="entries[1].description"/></td>
                                    <td><g:textField name="entries[1].comments"/></td>
                                    <td><input type="button" value="X" onclick="removeRow(this)" style="color: red; font-weight: 700;"/></td>
                                </tr>
                            </g:if>
                            <g:else>
                                <g:each in="${cmd?.entries.sort{a,b -> b.postingKey.name() <=> a.postingKey.name()}}" var="entry" status="i">
                                    <tr>
                                        <td><g:select name="entries[${i}].accountId" from="${Account.list(order)}" optionKey="id"
                                              value="${entry.accountId ?: 1}" optionValue="nameAndSide"/>
                                        </td>
                                        <td><g:select name="entries[${i}].postingKey" from="${Side.values()}" value="${entry.postingKey}"/></td>
                                        <td><g:textField name="entries[${i}].amount" value="${entry.amount}" size="8"/>
                                            <g:hiddenField name="entries[${i}].uuid" value="${entry.uuid}"/>
                                        </td>
                                        <td><g:textField name="entries[${i}].description" value="${entry.description}"/></td>
                                        <td><g:textField name="entries[${i}].comments" value="${entry.comments}"/></td>
                                        <td><input type="button" value="X" onclick="removeRow(this)" style="color: red; font-weight: 700;"/></td>
                                    </tr>
                                </g:each>
                            </g:else>
                        </tbody>
                    </table>
                    <fieldset class="buttons">
                        <g:submitButton name="${update ? 'Update' : 'Add'}" class="save"/>
                        <input type="button" id="add-row" value="Add Row" class="edit" onclick="addRow()"/>
                    </fieldset>
                </g:form>
            </div>
    </body>
</html>