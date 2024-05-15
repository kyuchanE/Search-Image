package chanq.search_image.ui.splash

import androidx.lifecycle.viewModelScope
import com.example.domain.model.CommonSearchResultData
import com.example.domain.model.SearchImageData
import com.example.domain.model.SearchVClipData
import com.example.domain.usecase.GetSearchImageUseCase
import com.example.domain.usecase.GetSearchVClipUseCase
import com.example.ui.common.BaseViewModel
import com.example.ui.utils.L
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(

): BaseViewModel(){

}