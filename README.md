# Beyefendi Sinemacı

A full-stack film review platform where admins can publish movie reviews and users can comment and manage their watchlist.

## Live Demo

https://beyefendisinemaci.com

## Tech Stack

**Backend:** Java, Spring Boot, Spring Security, JWT, Hibernate, MySQL, Redis, Bucket4j

**Frontend:** React, Html, CSS

**DevOps & Cloud:** Docker, AWS EC2, AWS ECR, AWS RDS, AWS S3, AWS CloudFront

## Features

- JWT authentication with role-based access control (Admin / User)
- Admins can add movies via TMDB API and publish video reviews (Poster photo, title and year come via TMDB)
- Admins can delete users and comments
- Users can comment on movies and manage a personal watchlist
- Media upload and delivery via AWS S3 and CloudFront
- Rate limiting on auth and upload endpoints with Bucket4j
- Fully containerized with Docker and deployed on AWS

## CI/CD

- Backend: GitHub Actions → Docker build → ECR push → EC2 deploy
- Frontend: GitHub Actions → npm build → S3 sync → CloudFront cache invalidation

## Screenshots

### Home Page

![Home Page](screenshots/Home%20Page.png)
_Film cards with watchlist functionality_

### Movie Details

![Movie Details](screenshots/Movie%20Details.png)
_Movie detail page with review, long and short video review_

### Comments

![Comments](screenshots/Movie%20Details%202.png)
_Comment section_

### User Profile - Comments

![Profile Page](screenshots/Profile%20Page.png)
_User profile showing comment history_

### User Profile - Watchlist

![Watchlist](screenshots/Profile%20Page%202%20.png)
_User watchlist with movie posters_

### Admin Panel - Edit & Delete Movies and Add Movie via TMDB

![Admin Panel Films](screenshots/Admin%20Panel%201%20.png)
_Admin can search and import movies directly from TMDB and Admin can edit or delete existing movies_

### Admin Panel - User Management

![Admin Panel Search](screenshots/Admin%20Panel%203%20.png)
_Admin can search, ban, promote or delete users_

### Add Movie Form

![Add Movie](screenshots/Add%20Movie.png)
_Admin fills in review, uploads poster and videos via S3_
