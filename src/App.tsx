/**
 * @license
 * SPDX-License-Identifier: Apache-2.0
 */

export default function App() {
  return (
    <div className="min-h-screen bg-gray-100 flex flex-col items-center justify-center p-4">
      <div className="bg-white p-8 rounded-xl shadow-lg max-w-2xl w-full text-center">
        <h1 className="text-3xl font-bold text-red-700 mb-4">AdGuard Permissions DZ</h1>
        <p className="text-gray-600 mb-6 text-lg">
          تم إنشاء مشروع تطبيق الأندرويد بنجاح!
        </p>
        <div className="bg-gray-50 p-4 rounded-lg text-left mb-6 border border-gray-200">
          <h2 className="font-semibold text-gray-800 mb-2">الملفات التي تم إنشاؤها:</h2>
          <ul className="list-disc list-inside text-sm text-gray-600 space-y-1">
            <li><code className="bg-gray-200 px-1 rounded">build.gradle.kts</code> (Root & App)</li>
            <li><code className="bg-gray-200 px-1 rounded">.github/workflows/build.yml</code> (GitHub Actions)</li>
            <li><code className="bg-gray-200 px-1 rounded">MainActivity.kt</code> (Jetpack Compose)</li>
            <li><code className="bg-gray-200 px-1 rounded">AppScanner.kt</code> (اكتشاف التطبيقات)</li>
            <li><code className="bg-gray-200 px-1 rounded">PermissionManager.kt</code> (إدارة الأذونات)</li>
            <li><code className="bg-gray-200 px-1 rounded">AppDatabase.kt</code> (Room DB)</li>
          </ul>
        </div>
        <p className="text-sm text-gray-500 mb-6">
          يمكنك الآن تصدير المشروع إلى GitHub عبر قائمة الإعدادات في AI Studio، وسيقوم GitHub Actions ببناء ملف الـ APK تلقائياً.
        </p>
      </div>
    </div>
  );
}
