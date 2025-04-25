const mongoose = require('mongoose');
const Product = require('../models/Product');

// Configuration de la connexion à MongoDB
mongoose.connect('mongodb://localhost:27017/microservice-db')
    .then(() => console.log('Connecté à MongoDB pour initialisation'))
    .catch(err => {
        console.error('Erreur de connexion MongoDB:', err);
        process.exit(1);
    });

// Données de test
const productsData = [
    {
        name: 'Smartphone XYZ',
        description: 'Un smartphone haut de gamme avec les dernières fonctionnalités',
        price: 899.99,
        category: 'Électronique',
        inStock: true
    },
    {
        name: 'Laptop Pro',
        description: 'Ordinateur portable pour les professionnels',
        price: 1299.99,
        category: 'Électronique',
        inStock: true
    },
    {
        name: 'Casque Audio Premium',
        description: 'Casque audio sans fil avec réduction de bruit',
        price: 249.99,
        category: 'Électronique',
        inStock: false
    },
    {
        name: 'T-shirt Coton Bio',
        description: 'T-shirt en coton biologique et équitable',
        price: 29.99,
        category: 'Vêtements',
        inStock: true
    },
    {
        name: 'Cafetière Italienne',
        description: 'Cafetière italienne traditionnelle en acier inoxydable',
        price: 34.99,
        category: 'Maison',
        inStock: true
    }
];

// Fonction pour initialiser la base de données
async function initializeDb() {
    try {
        // Supprimer tous les produits existants
        await Product.deleteMany({});
        console.log('Base de données nettoyée');

        // Insérer les nouvelles données
        const insertedProducts = await Product.insertMany(productsData);
        console.log(`${insertedProducts.length} produits ont été insérés avec succès`);

        // Fermer la connexion
        mongoose.connection.close();
        console.log('Connexion à MongoDB fermée');
    } catch (error) {
        console.error('Erreur lors de l\'initialisation de la base de données:', error);
    } finally {
        process.exit(0);
    }
}

// Exécuter l'initialisation
initializeDb(); 