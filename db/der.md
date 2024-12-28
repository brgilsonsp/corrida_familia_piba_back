```mermaid
---
    title: DER Corrida Família PIBA
---

erDiagram
    
    ATHLETE {
        INT id PK
        UUID id_uuid UK
        VARCHAR(30) document UK
        VARCHAR(150) name
        VARCHAR(10) gender
        DATE date_of_birth
        VARCHAR(30) modality
        INT bib_number
        VARCHAR(150) monitor
    }

    START_RACE {
        INT id PK
        UUID id_uuid UK
        INT bib_number
        TIME time_start
        VARCHAR(50) monitor
    }

    FINISH_RACE {
        INT id PK
        UUID id_uuid UK
        INT bib_number
        TIME time_finish
        VARCHAR(50) monitor
    }

    ATHLETE ||--o{ START_RACE  : "has"
    ATHLETE ||--o{ FINISH_RACE : "has"

```