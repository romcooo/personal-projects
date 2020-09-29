# Core
TODO

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

