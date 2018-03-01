[кеш]
При каждом выполнении запроса обновлять кеш

Используем Map

[CacheService]
синглтон
в реестр не кладем

get( channelId )
set( Channel )
remove()?
isExpired()?
update()?

get()
getStorage().getChannel(id)
If (storeDate+delta) < now: EXPIRED
If expired: return null and remove()

set()
getStorage(0).set(channel)
добавить дату

[!] expiration time одно для каналов, другое для видосов,
третье для каментов

[?] нужна ли потокобезопасность

Channel
    id
    name
    Set<Video>
    
Video
    name
    Set<Comment>
    
Comment
    author
    content
    date

// кешируем этот класс
Storage
    Set<Channel> channels
    
Можно добавить в настройки врем протухания
    
    