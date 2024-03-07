# Проект в рамках KODE IT Marathon`24
## _Задание_

 
В умном доме расположено несколько датчиков температуры, которые умеют сообщать температуру по протоколу HTTP в формате:
```sh
{
"sensorId": "5d316ee8-a785-4e87-91d8-06f901c98a88"
"temperatureC": 23.4
}
```

Необходимо реализовать библиотеку (пакет), экспортирующую функцию, которая принимает на вход список URL датчиков, опрашивает их  предпочтительно параллельно и возвращает данные о температуре на датчиках.

Необходимо предусмотреть 3 сценария завершения работы функции:
- все запросы завершились (не важно успехом или ошибкой)
- завершение по тайм-ауту
- отмена выполнения из внешнего (вызывающего функцию) кода

#### Stack

- Kotlin, Coroutines, Flow
- Dagger2
- Retrofit2 (OKHTTP под капотом отвечает за условие с тайм-аутом)

##### Как выполнял:

Экспортирующая функция возвращает список с содержимым, инкапуслированным абстракцией. Это публичный интерфейс, находщийся на домене, 
- предоставляет данные об id Датчика, 
- его температуре (если логика отработала, получив значение, отличное от Double.Nan)
- url Датчика, 
- сообщение, если получение данных было отличное от ожидаемого

`"параллельность"` выполнял посредством CoroutineScope.async {}
`отмену из вызывающего кода` выполнял следующим образом: (реализовано в RepositoryImpl) создал поток событий, который имеет в видимости CoroutineScope, Job'ы которого опрашивают датчики.
- При детекции команды на прерывании операции извне, поток активирует отмену этого CoroutineScope,
- Job'ы, которые еше не завершились - отменяются, в результате их отмены создаются объекты, сообщающие о том, что данные снять не получилось, и url датчика.

###### кое-что еще
- проект старается придерживаться философии чистоты архитектуры
- есть fake демонтрация функционала
- заложены к реализации нужные вещи, такие как валидация url, например