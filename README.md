## API Documentation

### Endpoints

| Method | Endpoint       | Description           | Parameters          | Headers                        | Body                                                              |
|--------|----------------|-----------------------|---------------------|--------------------------------|-------------------------------------------------------------------|
| GET    | /main          | 메인 페이지를 가져옵니다.        | -                   | -                              | -                                                                 |
| GET    | /posts         | 게시물 목록을 가져옵니다.        | -                   | -                              | -                                                                 |
| GET    | /posts/detail  | 게시물의 세부 정보를 가져옵니다.    | -                   | -                              | -                                                                 |
| GET    | /articles      | 게시물 목록을 가져옵니다.        | -                   | -                              | -                                                                 |
| GET    | /articles/{id} | 특정 게시물의 세부 정보를 가져옵니다. | `id` (path): 게시물 ID | -                              | -                                                                 |
| DELETE | /articles/{id} | 특정 게시물를 삭제합니다.        | `id` (path): 게시물 ID | -                              | -                                                                 |
| POST   | /articles      | 새 게시물를 작성합니다.         | -                   | Content-Type: application/json | `{ "authorId": 0, "boardId": 0, "title": "", "description": "" }` |
| PUT    | /articles/{id} | 기존 게시물를 업데이트합니다.      | `id` (path): 게시물 ID | Content-Type: application/json | `{ "boardId": 0, "title": "", "description": "" }`                |
| GET    | /boards        | 게시판 목록을 가져옵니다.        | -                   | -                              | -                                                                 |
| GET    | /boards/{id}   | 특정 게시판의 세부 정보를 가져옵니다. | `id` (path): 게시판 ID | -                              | -                                                                 |
| DELETE | /boards/{id}   | 특정 게시판을 삭제합니다.        | `id` (path): 게시판 ID | -                              | -                                                                 |
| POST   | /boards        | 새 게시판을 작성합니다.         | -                   | Content-Type: application/json | `{ "name": "" }`                                                  |
| PUT    | /boards/{id}   | 기존 게시판을 업데이트합니다.      | `id` (path): 게시판 ID | Content-Type: application/json | `{ "name": "" }`                                                  |
| GET    | /members       | 회원 목록을 가져옵니다.         | -                   | -                              | -                                                                 |
| GET    | /members/{id}  | 특정 회원의 세부 정보를 가져옵니다.  | `id` (path): 회원 ID  | -                              | -                                                                 |
| DELETE | /members/{id}  | 특정 회원을 삭제합니다.         | `id` (path): 회원 ID  | -                              | -                                                                 |
| POST   | /members       | 새 회원을 생성합니다.          | -                   | Content-Type: application/json | `{ "name": "", "email": "", "password": "" }`                     |
| PUT    | /members/{id}  | 기존 회원을 업데이트합니다.       | `id` (path): 회원 ID  | Content-Type: application/json | `{ "name": "", "email": "" }`                                     |

### 사용 예시

#### 게시물 생성

**Request:**

```bash
POST /articles
Content-Type: application/json

{
  "authorId": 1,
  "boardId": 2,
  "title": "New Article",
  "description": "This is a new article."
}
```

**Response:**

```json
HTTP/1.1 201 Created
Content-Type: application/json

{
"id": 1,
"authorId": 1,
"boardId": 2,
"title": "New Article",
"description": "This is a new article.",
"createdAt": "2024-05-28T10:00:00Z",
"updatedAt": "2024-05-28T10:00:00Z"
}
```

#### 게시물 단건 조회

**Request:**

```bash
GET /articles/1
```

**Response:**

```json
HTTP/1.1 200 OK
Content-Type: application/json

{
"id": 1,
"authorId": 1,
"boardId": 2,
"title": "New Article",
"description": "This is a new article.",
"createdAt": "2024-05-28T10:00:00Z",
"updatedAt": "2024-05-28T10:00:00Z"
}
```

#### 게시물 수정

**Request:**

```bash
PUT /articles/1
Content-Type: application/json

{
  "boardId": 2,
  "title": "Updated Article",
  "description": "This is an updated article."
}
```

**Response:**

```json
HTTP/1.1 200 OK
Content-Type: application/json

{
"id": 1,
"authorId": 1,
"boardId": 2,
"title": "Updated Article",
"description": "This is an updated article.",
"createdAt": "2024-05-28T10:00:00Z",
"updatedAt": "2024-05-28T12:00:00Z"
}
```

#### 게시물 삭제

**Request:**

```bash
DELETE /articles/1
```

**Response:**

```json
HTTP/1.1 204 No Content
```

```
