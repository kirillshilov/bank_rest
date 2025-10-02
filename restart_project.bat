@echo off
REM Получаем путь к текущей директории, где лежит этот .bat файл
SET PROJECT_DIR=%~dp0

REM Переходим в папку проекта
cd /d %PROJECT_DIR%

REM Останавливаем и удаляем старые контейнеры (без удаления томов)
docker-compose down

REM Пересобираем образы без использования кеша
docker-compose build --no-cache

REM Запускаем контейнер в фоне
docker-compose up

REM Выводим статус контейнера
docker-compose ps

echo.
echo Проект перезапущен! Можно закрыть окно
pause
