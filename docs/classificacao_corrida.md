```mermaid
---
    title: Classificação com corrida em aberto
---
sequenceDiagram
    actor monitor as Monitor
    participant app as App
    participant api as API
    participant db as Banco de Dados
    monitor->>app: Filtra classificacação
    app->>api: Solicita classificação com filtro
    api->>db: Status de encerramento corrida
    db-->>api: Sem status de encerramento
    api->>db: Registros inicio da corrida
    api->>db: Registros fim da corrida
    api->>db: Registros de atletas com número de peito
    loop cada atleta
        api->>api: Calcula tempo de corrida
        api->>api: Idade
    end
    api->>db: Salva classificação
    api->>db: Corrida encerrada
    api->>api: filtra classificação
    api-->>app: Classificação
    app-->>monitor: Exibe classificação
```

