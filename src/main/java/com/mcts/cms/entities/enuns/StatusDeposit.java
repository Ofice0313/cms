package com.mcts.cms.entities.enuns;

public enum StatusDeposit {

    PENDING,      // Aguardando pagamento
    IN_PROGRESS,  // Pagamento em andamento
    COMPLETED,    // Pagamento concluído
    OVERDUE,      // Pagamento atrasado
    CANCELLED     // Pagamento cancelado
}
