<?php

namespace App\Controller;

use App\Entity\Burger;
use App\Form\BurgerType;
use App\Repository\BurgerRepository;
use App\Service\ImageService;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

#[Route('/burger')]
final class BurgerController extends AbstractController
{
    #[Route(name: 'app_burger_index', methods: ['GET'])]
    public function index(BurgerRepository $burgerRepository): Response
    {
        return $this->render('burger/index.html.twig', [
            'burgers' => $burgerRepository->findAll(),
        ]);
    }

    #[Route('/new', name: 'app_burger_new', methods: ['GET', 'POST'])]
    public function new(
        Request $request,
        EntityManagerInterface $entityManager,
        ImageService $imageService
    ): Response {
        $burger = new Burger();
        $form = $this->createForm(BurgerType::class, $burger);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {

            // 👉 récupération de l'image depuis le formulaire
            $imageFile = $form->get('image')->getData();

            if ($imageFile) {
                $imageName = $imageService->upload($imageFile);
                $burger->setImage($imageName);
            }

            $entityManager->persist($burger);
            $entityManager->flush();

            return $this->redirectToRoute('app_burger_index');
        }

        return $this->render('burger/new.html.twig', [
            'burger' => $burger,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_burger_show', methods: ['GET'])]
    public function show(Burger $burger): Response
    {
        return $this->render('burger/show.html.twig', [
            'burger' => $burger,
        ]);
    }

    #[Route('/{id}/edit', name: 'app_burger_edit', methods: ['GET', 'POST'])]
    public function edit(
        Request $request,
        Burger $burger,
        EntityManagerInterface $entityManager,
        ImageService $imageService
    ): Response {
        $form = $this->createForm(BurgerType::class, $burger);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {

            // 👉 si on change l'image
            $imageFile = $form->get('image')->getData();

            if ($imageFile) {
                $imageName = $imageService->upload($imageFile);
                $burger->setImage($imageName);
            }

            $entityManager->flush();

            return $this->redirectToRoute('app_burger_index');
        }

        return $this->render('burger/edit.html.twig', [
            'burger' => $burger,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_burger_delete', methods: ['POST'])]
    public function delete(
        Request $request,
        Burger $burger,
        EntityManagerInterface $entityManager
    ): Response {
        if ($this->isCsrfTokenValid('delete'.$burger->getId(), $request->getPayload()->getString('_token'))) {
            $entityManager->remove($burger);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_burger_index');
    }
}
