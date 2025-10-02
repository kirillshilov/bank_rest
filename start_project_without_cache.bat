@echo off
REM Получаем путь к текущей директории, где лежит этот .bat файл
SET PROJECT_DIR=%~dp0

REM Переходим в папку проекта
cd /d %PROJECT_DIR%

REM Останавливаем и удаляем старые контейнеры, тома и орфанные контейнеры (удаляем все данные)
docker-compose down --volumes --remove-orphans

REM Пересобираем образы без использования кеша
docker-compose build --no-cache

REM Запускаем контейнер в фоне
docker-compose up -d

REM Выводим статус контейнера
docker-compose ps

echo.
echo Проект перезапущен с удалением данных!  Можно закрыть окно
pause