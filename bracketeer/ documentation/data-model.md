# Core

## Tournament
| Column Name  | Data Type    | Nullable? | Note                              |
| ----         | ----         | ----      | ----                              |
| id           | int          | false     | -                                 |
| code         | varchar(255) | true      | -                                 |
| name         | varchar(255) | true      | -                                 |
| type         | varchar(45)  | true      | move to rule_set                  |
| organized_by | varchar(45)  | true      | TODO implement (username of user) |

## Round
| Column Name   | Data Type          | Nullable? | Note |
| ----          | ----               | ----      | ---- |
| id            | int                | false     | -    |
| tournament_id | FK - Tournament.id | false     | -    |
| round_number  | int                | true      | -    |
| best_of       | int                | true      | -    |

## Match
| Column Name  | Data Type     | Nullable? | Note |
| ----         | ----          | ----      | ---- |
| id           | int           | false     | -    |
| is_bye       | tinyint       | true      | -    |
| round_id     | FK - Round.id | false     | -    |
| match_number | int           | true      | -    |

## Match Result
| Column Name    | Data Type           | Nullable? | Note |
| ----           | ----                | ----      | ---- |
| participant_id | FK - Participant.id | false     | -    |
| match_id       | FK - Match.id       | false     | -    |
| games_won      | int                 | true      | -    |

## Participant
| Column Name    | Data Type          | Nullable? | Note                              |
| ----           | ----               | ----      | ----                              |
| id             | int                | false     | -                                 |
| name           | varchar(255)       | true      | -                                 |
| code           | varchar(255)       | true      | -                                 |
| starting_score | int                | true      | -                                 |
| starting_byes  | int                | true      | -                                 |
| tournament_id  | FK - Tournament.id | false     | -                                 |
| of_user        | varchar(45)        | true      | TODO implement (username of user) |

## Rule Set
| Column Name        | Data Type          | Nullable? | Note                               |
| ----               | ----               | ----      | ----                               |
| id                 | int                | false     | -                                  |
| points_for_victory | double             | true      | TODO - rename from points_for_win  |
| points_for_loss    | double             | true      | -                                  |
| points_for_tie     | double             | true      | -                                  |
| best_of            | int                | true      | TODO - rename from default_best_of |
| tournament_id      | FK - Tournament.id | false     | -                                  |


# User Management

## User
| Column Name               | Data Type    | Is Nullable? |
| ----                      | ----         | ----         |
| id                        | long         | false        |
| username                  | varchar(45)  | false        |
| password_hash             | varchar(100) | false        |
| creation_date             | datetime     | false        |
| last_update_date          | datetime     | true         |
| last_password_change_date | datetime     | true         |
| email                     | varchar(100) | false        |

## User2Role
| Column Name | Data Type | Is Nullable? |
| ----        | ----      | ----         |
| user_id     | long      | false        |
| role_id     | long      | false        |

## Role
| Column Name | Data Type   | Is Nullable? |
| ----        | ----        | ----         |
| id          | long        | false        |
| name        | varchar(45) | false        |
TODO

## Role2Privilege
| Column Name  | Data Type | Is Nullable? |
| ----         | ----      | ----         |
| role_id      | long      | false        |
| privilege_id | long      | false        |

## Privilege
| Column Name | Data Type    | Is Nullable? |
| ----        | ----         | ----         |
| id          | long         | false        |
| name        | varchar(100) | false        |

