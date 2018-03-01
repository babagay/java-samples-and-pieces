[кеш]
При каждом выполнении запроса обновлять кеш

Используем Map

[Backlog]
 Реализовать кеш-машину в виде одиночки CacheService
     get( String channelIds ) // example: get("A") get("A,B")
     set( Channel channel ) // example: set(channelA)
     setVideos( Channel channel, List<Video> videos )
 Реализовать кеш Level-1 и кеш Level-2.
    L1 кеширует все ответы.
    L2 кеширует объекты каналов.
 Можно добавить в настройки время протухания
 Expiration time одно для каналов, другое для видосов, третье для каментов
    если мы отдали канал (он свежий), а его видосы протухли, сбрасываем их в NULL (а после фетча ядро может обновить их)    
 
 Если канал был отдан пользователю из кеша, то после этого мы можем его освежить в отдельном потоке
 Сбрасывать кеш на диск перед закрытием программы
 Проверить, что после обновления канала в L2 кеше он обновится и в L1. Можно написать тест.
 
 [?] нужна ли потокобезопасность

[CacheService]
синглтон
в реестр не кладем

get( channelId )
set( Channel )
setVideos( channelId )
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

[кеш Level-1] хранит результаты запросов
<String> : <Node>
            channelsList[]

[кеш Level-2] хранит , собственно, объекты каналов
<String> : <Channel>
            channelsList[]    
            
    сериализуем этот класс:
     Storage
         Set<Channel> channels
    
[примеры работы кеш-машины]
    Дай мне канал Б
    В L1 есть Б? 
    Нет
    В L2 есть Б?
    Нет
    Вернуть NULL
    ...
    Б успешно загружен
    set(Б)
        Положить Б в кеш L2
        В L1 есть запись "Б"? 
        Нет
        Создать Node(Б) и положить ее в L1
        
    Дай канал А
    В L1 есть запись "А"?
    Нет
    В L2 есть запись "А"?
    Да
    Вернуть А
    Создать Node(А) и положить ее в L1
    
    Дай канал В
    В L1 есть запись "В"?
    Да
    Вернуть В
    ...
    В передан во View
    Загрузить в бэкграунде В и выполнить set(В)
        Положить В в кеш L2
        ! Проверить: если в L1 уже есть запись 'В' и мы ее не обновляем, будет ли она ссылаться на обновленный объект В
        
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    