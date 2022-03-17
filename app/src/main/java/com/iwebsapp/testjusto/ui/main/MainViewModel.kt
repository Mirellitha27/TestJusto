package com.iwebsapp.testjusto.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iwebsapp.testjusto.api.ApiRepository
import com.iwebsapp.testjusto.api.ApiService
import com.iwebsapp.testjusto.model.HomeModel
import com.iwebsapp.testjusto.model.error.ErrorApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainViewModel : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private val data: MutableLiveData<HomeModel> = MutableLiveData()
    private var error: MutableLiveData<ErrorApi> = MutableLiveData()

    fun getData() = data

    private fun setMyData(myData: HomeModel) {
        this.data.postValue(myData)
    }

    private fun setError(errorApi: ErrorApi) {
        this.error.postValue(errorApi)
    }

    fun getDataUser() {
        val apiRepository = ApiRepository()
        val disposable: Disposable
        disposable = apiRepository.getService(ApiService::class.java)
            .getRandomUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                setMyData(response)
            }, { throwable ->
                setError(ErrorApi(apiRepository.getErrorResponse(throwable).error.message))
            })
        compositeDisposable.add(disposable)
    }

    private fun clearDisposable() {
        compositeDisposable.clear()
    }

    fun onDestroy() {
        clearDisposable()
    }
}