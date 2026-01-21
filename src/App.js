import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import Navbar from "./components/common/Navbar.jsx";
import Footer from "./components/common/Footer.jsx";
import RegisterPage from "./components/auth/RegisterPage.jsx";
import LoginPage from "./components/auth/LoginPage.jsx";
import HomePage from "./components/home_menu/HomePage.jsx";
import CategoriesPage from "./components/home_menu/CategoriesPage.jsx";
import MenuPage from "./components/home_menu/MenuPage.jsx";
import MenuDetailsPage from "./components/home_menu/MenuDetailsPage.jsx";
import ProfilePage from "./components/profile_cart/ProfilePage.jsx";
import UpdateProfilePage from "./components/profile_cart/UpdateProfilePage.jsx";
import OrderHistoryPage from "./components/profile_cart/OrderHistoryPage.jsx";
import { AdminRoute, CustomerRoute } from "./services/Guard.js";
import LeaveReviewPage from "./components/profile_cart/LeaveReviewPage.jsx";
import CartPage from "./components/profile_cart/CartPage.jsx";
import ProcessPaymenttPage from "./components/payment/ProcessPaymenttPage.jsx";
import AdminLayout from "./components/admin/navbar/AdminLayout.jsx";
import AdminCategoriesPage from "./components/admin/AdminCategoriesPage.jsx";
import AdminCategoryFormPage from "./components/admin/AdminCategoryFormPage.jsx";
import AdminMenuPage from "./components/admin/AdminMenuPage.jsx";
import AdminMenuFormPage from "./components/admin/AdminMenuFormPage.jsx";
import AdminOrdersPage from "./components/admin/AdminOrdersPage.jsx";
import AdminOrderDetailPage from "./components/admin/AdminOrderDetailPage.jsx";
import AdminPaymentsPage from "./components/admin/AdminPaymentsPage.jsx";
import AdminPaymentDetailPage from "./components/admin/AdminPaymentDetailPage.jsx";
import AdminDashboardPage from "./components/admin/AdminDashboardPage.jsx";
import AdminUserRegistration from "./components/auth/AdminUserRegistration.jsx";

function App() {
  return (
    <BrowserRouter>
      <Navbar />
      <div className="content">
        <Routes>
          {/* AUTH PAGES */}
          <Route path="/register" element={<RegisterPage />} />
          <Route path="/login" element={<LoginPage />} />

          <Route path="/home" element={<HomePage />} />
          <Route path="/categories" element={<CategoriesPage />} />

          <Route path="/menu" element={<MenuPage />} />
          <Route path="/menu/:id" element={<MenuDetailsPage />} />

          <Route
            path="/profile"
            element={<CustomerRoute element={<ProfilePage />} />}
          />

          <Route
            path="/update"
            element={<CustomerRoute element={<UpdateProfilePage />} />}
          />

          <Route
            path="/my-order-history"
            element={<CustomerRoute element={<OrderHistoryPage />} />}
          />

          <Route
            path="/leave-review"
            element={<CustomerRoute element={<LeaveReviewPage />} />}
          />

          <Route
            path="/cart"
            element={<CustomerRoute element={<CartPage />} />}
          />

          <Route
            path="/pay"
            element={<CustomerRoute element={<ProcessPaymenttPage />} />}
          />

          {/* ADMIN ROUTES */}

          <Route
            path="/admin"
            element={<AdminRoute element={<AdminLayout />} />}
          >
            <Route path="categories" element={<AdminCategoriesPage />} />
            <Route path="categories/new" element={<AdminCategoryFormPage />} />
            <Route
              path="categories/edit/:id"
              element={<AdminCategoryFormPage />}
            />

            <Route path="menu-items" element={<AdminMenuPage />} />
            <Route path="menu-items/new" element={<AdminMenuFormPage />} />
            <Route path="menu-items/edit/:id" element={<AdminMenuFormPage />} />

            <Route path="orders" element={<AdminOrdersPage />} />
            <Route path="orders/:id" element={<AdminOrderDetailPage />} />

            <Route path="payments" element={<AdminPaymentsPage />} />

            <Route path="payments/:id" element={<AdminPaymentDetailPage />} />

            <Route index element={<AdminDashboardPage />} />

            <Route path="register" element={<AdminUserRegistration />} />
          </Route>

          <Route path="*" element={<Navigate to={"/home"} />} />
        </Routes>
      </div>
      <Footer />
    </BrowserRouter>
  );
}

export default App;
