const express = require('express');
const mongoose = require('mongoose');
const cors = require('cors');
const path = require('path');
const fs = require('fs');
const productRoutes = require('./routes/productRoutes');
const schoolRoutes = require('./routes/schoolRoutes');

const app = express();
const PORT = process.env.PORT || 3000;

// Middleware pour parser le JSON et autoriser CORS
app.use(express.json());
app.use(cors());

// Répertoire pour les fichiers statiques
app.use(express.static(path.join(__dirname, 'public')));

// Créer le répertoire pour les PDFs s'il n'existe pas
const pdfDir = path.join(__dirname, 'public/pdf');
if (!fs.existsSync(pdfDir)) {
    fs.mkdirSync(pdfDir, { recursive: true });
}

// Route de base
app.get('/', (req, res) => {
    res.json({ message: 'Microservice Express fonctionne correctement!' });
});

// Routes API
app.use('/api/products', productRoutes);
app.use('/api/schools', schoolRoutes);

// Connexion à MongoDB
mongoose.connect('mongodb://localhost:27017/microservice-db')
    .then(() => console.log('Connecté à MongoDB'))
    .catch(err => console.error('Erreur de connexion MongoDB:', err));

// Démarrage du serveur
app.listen(PORT, () => {
    console.log(`Serveur démarré sur le port ${PORT}`);
}); 