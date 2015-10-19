<%@page import="com.admu.accountinggroup.domain.*" %>
<%@page import="com.admu.accountinggroup.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <title>T-Account Approved Entries</title>
    </head>
    <body>
            <div class="content scaffold-list" role="main">
                <h1>T-Account Approved Entries</h1>
                <div class="nav" role="navigation">
                    <ul>
                        <li><g:link class="home" action="index">Back</g:link></li>
                        <li><g:link class="create" action="entryAdd">Add T-Account Entry</g:link></li>
                        <li><g:link class="home" action="chart">Show Benchmark</g:link></li>
                    </ul>
                </div>
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
                                <td><b>Ref. id: ${doc.id}</b></td>
                                <td>Ref: ${doc.reference}</td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <g:each in="${doc.transactions}" var="transaction" status="i">
                                <tr>
                                    <td>
                                        <g:if test="${i == 0}">
                                           ${doc.documentDateFormatted}
                                        </g:if>
                                    </td>
                                    <td>${transaction.account}</td>
                                    <td style="text-align:right;">
                                        <g:if test="${Side.DR == transaction.postingKey}">
                                            ${transaction.amount}
                                        </g:if>
                                    </td>
                                    <td style="text-align:right;">
                                        <g:if test="${Side.CR == transaction.postingKey}">
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
                    <fieldset class="buttons">
                        <input type="button" value="Approve" class="save"
                           onclick="location.href = '${createLink(action:'approveTempEntries')}';"/>
                    </fieldset>
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