bash_history_backup
# Установка SSH (опционально)
sudo apt update
sudo apt install openssh-server
sudo systemctl enable --now ssh
sudo systemctl status ssh
ip a

# Установка Java 17 (так как в вашем pom.xml указана версия 17)
sudo apt update
sudo apt install -y openjdk-17-jdk
java -version

# Создание директории и клонирование репозитория
mkdir -p ~/app
cd ~/app
git clone https://github.com/ariverix/api-service.git
cd api-service
ls

# Сборка проекта
chmod +x mvnw
./mvnw package

# Создание директории для деплоя и копирование jar файла
sudo mkdir -p /var/www/app
sudo cp target/api-service-0.0.1-SNAPSHOT.jar /var/www/app/app.jar        

или 
sudo cp target/api-service.jar /var/www/app/app.jar

sudo chmod 755 /var/www/app

# Создание systemd сервиса
sudo nano /etc/systemd/system/apiservice.service

# Содержимое файла /etc/systemd/system/apiservice.service:
[Unit]
Description=Spring Boot API Service
After=network.target

[Service]
User=ariver
WorkingDirectory=/var/www/app
ExecStart=/usr/bin/java -jar /var/www/app/app.jar
SuccessExitStatus=143
TimeoutStopSec=10
Restart=on-failure
RestartSec=5

[Install]
WantedBy=multi-user.target

# Запуск сервиса
sudo systemctl daemon-reload
sudo systemctl enable apiservice
sudo systemctl start apiservice
sudo systemctl status apiservice

# Проверка работы приложения
curl http://localhost:5000/health
sudo ss -tulnp | grep java

# Установка и настройка Nginx
sudo apt install -y nginx
sudo nano /etc/nginx/sites-available/apiservice

# Содержимое файла /etc/nginx/sites-available/apiservice:
server {
    listen 80;
    server_name 192.168.1.225;
    location / {
        proxy_pass http://localhost:5000;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
}

или
server {
    listen 80;
    server_name localhost;
    location / {
        proxy_pass http://localhost:5000;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
}

или

server {
    listen 80;
    server_name 192.168.1.225;  # Ваш IP

    # Проксируем ВСЕ запросы на Java-приложение
    location / {
        proxy_pass http://localhost:5000;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }
}

# Активация конфигурации Nginx
sudo ln -s /etc/nginx/sites-available/apiservice /etc/nginx/sites-enabled/
sudo rm /etc/nginx/sites-enabled/default
sudo nginx -t
sudo systemctl restart nginx

# Финальная проверка
ip a
sudo lsof -i -P -n | grep LISTEN
curl http://localhost/health
curl http://192.168.1.225/health

# Дополнительные проверки API
curl http://192.168.1.225/api/users
curl http://192.168.1.225/api/products
curl http://192.168.1.225/api/users/1
curl http://192.168.1.225/api/products/1

или
curl http://localhost/api/users
curl http://localhost/api/products
curl http://localhost/api/users/1
curl http://localhost/api/products/1


# Вывод истории команд
history

Завершение
sudo pkill -f ":5000"
Проверка
sudo lsof -i :5000
