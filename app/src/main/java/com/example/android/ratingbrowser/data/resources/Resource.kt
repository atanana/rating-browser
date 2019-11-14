package com.example.android.ratingbrowser.data.resources

import com.example.android.ratingbrowser.data.Queries
import com.example.android.ratingbrowser.data.db.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

abstract class Resource<Data, Payload>(
    protected val queries: Queries,
    protected val database: AppDatabase,
    private val scope: CoroutineScope = GlobalScope
) {
    protected abstract suspend fun getFromDb(payload: Payload): Data?

    protected abstract suspend fun saveToDb(data: Data)

    protected abstract suspend fun getFromNetwork(payload: Payload): Data

    private suspend fun update(payload: Payload): Data {
        val data = getFromNetwork(payload)
        saveToDb(data)
        return data
    }

    suspend fun get(payload: Payload): Data {
        val result = getFromDb(payload)
        return if (result != null) {
            scope.launch {
                update(payload)
            }
            result
        } else {
            update(payload)
        }
    }
}