<%@page import="com.admu.accountinggroup.domain.*" %>
<%@page import="com.admu.accountinggroup.*" %>
<%@page import="com.admu.accountinggroup.utils.DateUtils" %>
<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <title>T-Account Cache Entries</title>
    </head>
    <body>
            <div class="content scaffold-list" role="main">
                <h1>T-Account Cache Entries</h1>
                <g:if test="${flash.message}">
                    <div class="message" role="status">${flash.message}</div>
                </g:if>
                <div class="nav" role="navigation">
                    <ul>
                        <li><g:link class="create" action="entryAdd">Add T-Account Entry</g:link></li>
                        <li><g:link class="create" action="sync">Sync Cache to DB</g:link></li>
                        <li><g:link class="home" controller="transactionAccountEntry" action="approvedEntries" target="_blank">Synced Data</g:link></li>
                        <li><g:link class="home" controller="quartz" action="list" target="_blank">Sync Sched. Details</g:link></li>
                        <li><g:link class="create" action="scheduleThreeMinutesFromNow">Sched. Sync in 3 mins.</g:link></li>
                    </ul>
                </div>
                <br/>
                <g:if test="${documents}">
                    <table border="1">
                        <tr>
                            <th>Date</th>
                            <th>Particular</th>
                            <th>DR</th>
                            <th>CR</th>
                            <th>Comments</th>
                            <th>Description</th>
                        </tr>
                        <g:each in="${documents}" var="doc">
                            <tr>
                                <td></td>
                                <td>Ref: <g:link action="details" params="[uuid : doc.uuid]">${doc.reference}</g:link></td>
                                <td>Synced: <g:formatBoolean boolean="${doc.synced}" false="No" true="Yes"/></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <g:each in="${doc.transactions.sort{a,b -> b.postingKey.name <=> a.postingKey.name}}" var="transaction" status="i">
                                <tr>
                                    <td>
                                        <g:if test="${i == 0}">
                                           <g:formatDate format="MM/dd" date="${DateUtils.convertToDate(doc.documentDate)}"/>
                                        </g:if>
                                    </td>
                                    <td>${Account.get(transaction.account.id).name}</td>
                                    <td style="text-align:right;">
                                        <g:if test="${Side.DR == Side.valueOf(transaction.postingKey.name)}">
                                            ${transaction.amount}
                                        </g:if>
                                    </td>
                                    <td style="text-align:right;">
                                        <g:if test="${Side.CR == Side.valueOf(transaction.postingKey.name)}">
                                            ${transaction.amount}
                                        </g:if>
                                    </td>
                                    <td>${transaction.comment}</td>
                                    <td>${transaction.description}</td>
                                </tr>
                            </g:each>
                            <tr>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                        </g:each>
                        <tr>
                        </tr>
                    </table>
                </g:if>
                <g:else>
                    <p align="center">
                        <br/>
                        <b>Nothing to Show</b>
                        <br/>
                    </p>
                </g:else>
            </div>
    </body>
</html>