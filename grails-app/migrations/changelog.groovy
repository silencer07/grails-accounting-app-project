databaseChangeLog = {
    changeSet(author: '..', id: '..') {
        createView("""
          SELECT a.id as account_id,
                 (SELECT COALESCE(sum(amount),0)
                  FROM transactions
                  WHERE account_id = a.id
                    AND posting_key = 'DR') AS debit,
                 (SELECT COALESCE(sum(amount),0)
                  FROM transactions
                  WHERE account_id = a.id
                    AND posting_key = 'CR') AS credit
          FROM accounts a;
        """, viewName: 'account_summaries')
    }
}
