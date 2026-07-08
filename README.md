# 🚀 QuickNotes AI

> **Your Smart AI Study Companion**

QuickNotes AI is a modern Android application built using **Kotlin** and **Jetpack Compose** that helps students generate AI-powered study materials, practice quizzes, organize tasks, and learn more efficiently.

Instead of switching between multiple apps for notes, quizzes, reminders, and revision, QuickNotes AI brings everything together in one clean and intuitive application.

---

## 📱 Features

### 📚 AI Study Notes
Generate well-structured study notes for any topic using AI.

- Easy-to-understand explanations
- Quick revision material
- Concept-focused learning

---

### 📝 Personalized MCQs

Practice AI-generated quizzes based on your selected difficulty level.

- Easy
- Medium
- Hard

Includes:

- Instant scoring
- Detailed explanations
- Correct answers
- Performance tracking

---

### 📄 Learn From PDFs & Images

Upload your study material using:

- Camera
- Gallery
- PDF files

The app extracts text and generates:

- Study Notes
- Summaries
- MCQs
- Important Questions

---

### 🤖 AI Doubt Solving

Continue learning by asking follow-up questions related to your generated notes.

Get clear explanations whenever you need them.

---

### 📥 Offline Study History

All generated content is saved locally.

You can revisit anytime:

- Study Notes
- Summaries
- Quiz Results
- MCQs

No internet required for previously saved content.

---

### 📅 Study Planner

Stay organized using the built-in planner.

Features:

- Create daily tasks
- Reminder notifications
- Mark completed tasks

---

### 🌙 Light & Dark Theme

Choose your preferred appearance.

- Light Theme
- Dark Theme

---

### 🔐 Authentication

- Firebase Authentication
- Secure user login
- Personalized usage tracking

---

### ☁️ Usage Management

Uses Firebase Firestore to manage free AI usage limits.

Each user has:

- Usage Count
- Remaining Credits

---

## 🏗 Tech Stack

| Category | Technology |
|----------|------------|
| Language | Kotlin |
| UI | Jetpack Compose |
| Architecture | Clean Architecture + MVVM |
| Dependency Injection | Hilt |
| Database | Room |
| Cloud | Firebase Authentication & Firestore |
| OCR | Google ML Kit |
| AI | Gemini API |
| Async | Kotlin Coroutines & Flow |
| Navigation | Navigation Compose |
| Preferences | DataStore |
| Notifications | AlarmManager + BroadcastReceiver |

---

# 📂 Project Structure

```
com.example.quicknotes
│
├── data
│   ├── mapper
│   ├── preference
│   ├── quiz
│   ├── remote
│   ├── repository
│   ├── study
│   └── todo
│
├── domain
│   ├── model
│   ├── repository
│   └── usecase
│
├── navigation
│
├── notification
│
├── screens
│   ├── authentication
│   ├── home
│   ├── downloads
│   ├── quiz
│   ├── todo
│   ├── settings
│   └── profile
│
├── viewmodel
│
├── ui
│
└── MainActivity
```

---

# 🧱 Architecture

QuickNotes AI follows **Clean Architecture** with **MVVM**, ensuring scalability, maintainability, and separation of concerns.

```
Presentation Layer
│
├── Jetpack Compose Screens
├── ViewModels
└── Navigation

        ↓

Domain Layer
│
├── Models
├── Repository Interfaces
└── Use Cases

        ↓

Data Layer
│
├── Repository Implementations
├── Room Database
├── Firebase
├── DataStore
├── ML Kit
└── AI Services
```

---

# 📂 Layer Explanation

## Presentation Layer

Responsible for UI.

Contains:

- Jetpack Compose Screens
- ViewModels
- Navigation
- State Management

---

## Domain Layer

Contains business logic.

Includes:

- Models
- Repository Interfaces
- Use Cases

This layer has **no dependency** on Android Framework.

---

## Data Layer

Responsible for fetching and storing data.

Includes:

- Room Database
- Firebase Firestore
- Firebase Authentication
- AI API
- DataStore
- OCR
- Repository Implementations

---

# 🧠 Core Modules

### AI Module

Generates:

- Study Notes
- Summaries
- MCQs
- Important Questions
- AI Chat Responses

---

### Quiz Module

- Generates quizzes
- Saves history
- Shows explanations
- Calculates score

---

### History Module

Stores:

- Notes
- Quiz Results
- Summaries

Using Room Database.

---

### Planner Module

Manage daily study schedule.

Includes:

- Task Creation
- Reminder Notifications
- Completion Status

---

### OCR Module

Extract text from:

- Images
- Camera
- PDF Files

Using Google ML Kit.

---

# 📦 Database

Uses **Room Database**.

Main Tables:

- StudyHistory
- QuizHistory
- Todo

---

# 🔥 Firebase

Used for:

- Authentication
- Firestore Usage Tracking

---

# 📸 Screens

- Login
- Register
- Home
- AI Notes Generator
- Quiz Screen
- Downloads
- History Details
- Quiz Details
- Planner
- Settings
- Profile

---

# ✨ Highlights

- Modern Material Design 3 UI
- Clean Architecture
- MVVM
- Hilt Dependency Injection
- Offline Room Database
- AI-powered Learning
- OCR Support
- Reminder Notifications
- Firebase Authentication
- Firestore Integration
- Light & Dark Theme
- Responsive UI

---

# 🚀 Future Improvements

- Voice-based AI assistant
- Flashcards
- Mind Maps
- Cloud Backup
- Subject-wise Analytics
- Multi-language Support
- Export Notes as PDF

---

# 🎯 Ideal For

- School Students
- College Students
- Competitive Exam Aspirants
- Self Learners
- Teachers

---

# 🤝 Contributing

Contributions are always welcome.

1. Fork the repository

2. Create a feature branch

```
git checkout -b feature/NewFeature
```

3. Commit changes

```
git commit -m "Added New Feature"
```

4. Push branch

```
git push origin feature/NewFeature
```

5. Open a Pull Request

---

# 📄 License

This project is developed for educational purposes.

---

# 👨‍💻 Developer

**Prem Doba**

Android Developer | Kotlin | Jetpack Compose | Firebase | Clean Architecture | AI Integration

GitHub:
https://github.com/premdoba

---

## ⭐ If you like this project

Give it a ⭐ on GitHub and support the project!

> **Study Less. Learn More.**
