# Beyefendi Sinemacı
A full-stack film review platform where admins can publish movie reviews and users can comment and manage their watchlist.

## Live Demo
https://beyefendisinemaci.com

## Tech Stack
**Backend:** Java, Spring Boot, Spring Security, JWT, Hibernate, MySQL

**Frontend:** React, Vite

**DevOps & Cloud:** Docker, AWS EC2, AWS RDS, AWS S3, AWS CloudFront

## Features
- JWT authentication with role-based access control (Admin / User)
- Admins can add movies via TMDB API and publish video reviews (Poster photo, title and year come via TMDB)
- Admins can delete users and comments 
- Users can comment on movies and manage a personal watchlist
- Media upload and delivery via AWS S3 and CloudFront
- Rate limiting on auth and upload endpoints with Bucket4j
- Fully containerized with Docker and deployed on AWS

## Screenshots 
### Home Page
![Home Page](screenshots/Home%20Page.png)
*Film cards with watchlist functionality*
### Movie Details
![Movie Details](screenshots/Movie%20Details.png)
*Movie detail page with review, long and short video review*
### Comments
![Comments](screenshots/Movie%20Details%202.png)
*Comment section*
### User Profile - Comments
![Profile Page](screenshots/Profile%20Page.png)
*User profile showing comment history*
### User Profile - Watchlist
![Watchlist](screenshots/Profile%20Page%202%20.png)
*User watchlist with movie posters*
### Admin Panel - Edit & Delete Movies and Add Movie via TMDB
![Admin Panel Films](screenshots/Admin%20Panel%201%20.png)
*Admin can search and import movies directly from TMDB and Admin can edit or delete existing movies*
### Admin Panel - User Management
![Admin Panel Search](screenshots/Admin%20Panel%203%20.png)
*Admin can search, ban, promote or delete users*
### Add Movie Form
![Add Movie](screenshots/Add%20Movie.png)
*Admin fills in review, uploads poster and videos via S3*
