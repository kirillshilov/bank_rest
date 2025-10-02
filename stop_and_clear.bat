@echo off
REM Получаем путь к текущей директории, где лежит этот .bat файл
SET PROJECT_DIR=%~dp0

REM Переходим в папку проекта
cd /d %PROJECT_DIR%

REM Останавливаем и удаляем контейнеры, тома и сети, связанные с этим проектом
docker-compose down -v --rmi local --remove-orphans

echo.
echo Все следы проекта удалены! Можно закрыть окно
pause
