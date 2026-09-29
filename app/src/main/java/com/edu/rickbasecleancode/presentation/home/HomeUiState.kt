package com.edu.rickbasecleancode.presentation.home

import com.edu.rickbasecleancode.domain.model.Character

//Uma interface selada restringe quais tipos podem implementá-la.
// Todos os estados possíveis da tela Home têm de estar declarados
// aqui dentro (ou no mesmo ficheiro).
sealed interface HomeUiState {
    //object (Singleton): Como o estado de carregamento é sempre o mesmo e não carrega nenhum dado adicional
    // (não há variáveis aqui dentro), usamos um object em vez de uma classe. Isto otimiza a memória,
    // pois é criada apenas uma única instância deste estado na aplicação inteira sempre que a tela está
    // a carregar.
    object Loading : HomeUiState
    data class Success(val characters: List<Character>) : HomeUiState
    data class Error(val message: String) : HomeUiState
}
